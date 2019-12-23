package com.learning.hotcompile;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ClassMemCompiler {

    /**
     * dynamic compile and load class
     */
    public static Class<?> compileAndLoad(String fullClassName, String sourceCode) throws MemCompileException, ClassNotFoundException {
        StandardJavaFileManager standardJavaFileManager = ToolProvider.getSystemJavaCompiler().getStandardFileManager(null, null, null);
        MemFileManager memFileManager = new MemFileManager(standardJavaFileManager);
        try {
            doCompile(memFileManager, fullClassName, sourceCode);
        } catch (MemCompileException memCplExp) {
            for (Diagnostic<? extends JavaFileObject> diagnostic : memCplExp.getDiagnosticList()) {
                log.error("compile in memory error!! fullClassName={}, diagnostic={}", fullClassName, diagnostic);
            }
            throw memCplExp;
        }

        MemClassLoader memClassLoader = new MemClassLoader(memFileManager);
        try {
            return memClassLoader.loadClass(fullClassName);
        } catch (ClassNotFoundException e) {
            log.error("mem class loader error!! fullClassName={}", fullClassName, e);
            throw e;
        }
    }

    private static JavaMemClassFile doCompile(MemFileManager memFileManager, String fullClassName, String sourceCode) throws MemCompileException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();

        JavaSourceFileFromString sourceCodeFromString = new JavaSourceFileFromString(fullClassName, sourceCode);
        JavaCompiler.CompilationTask compilationTask = compiler.getTask(null, memFileManager, diagnosticCollector, null, null, Lists.newArrayList(sourceCodeFromString));
        boolean success = compilationTask.call();
        if (!success) {
            throw new MemCompileException(fullClassName, diagnosticCollector.getDiagnostics());
        }
        log.info("compile in memory success! fullClassName={}", fullClassName);
        JavaMemClassFile javaMemClassFile = memFileManager.cachedMemJavaClassFiles.get(fullClassName);
        return javaMemClassFile;
    }


    public static class JavaSourceFileFromString extends SimpleJavaFileObject {

        private String sourceCode;

        protected JavaSourceFileFromString(String name, String sourceCode) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.sourceCode = sourceCode;
        }


        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return sourceCode;
        }
    }

    public static class JavaMemClassFile extends SimpleJavaFileObject {

        private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        private String fullName;

        protected JavaMemClassFile(String name) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
            fullName = name;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return byteArrayOutputStream;
        }

        public String getFullName() {
            return fullName;
        }
    }


    public static class MemFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

        private final Map<String, JavaMemClassFile> cachedMemJavaClassFiles = new HashMap<>();

        /**
         * Creates a new instance of ForwardingJavaFileManager.
         *
         * @param fileManager delegate to this file manager
         */
        protected MemFileManager(StandardJavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            if (kind != JavaFileObject.Kind.CLASS) {
                return super.getJavaFileForOutput(location, className, kind, sibling);
            }
            if (cachedMemJavaClassFiles.containsKey(className)) {
                return cachedMemJavaClassFiles.get(className);
            }
            JavaMemClassFile javaClassFile = new JavaMemClassFile(className);
            cachedMemJavaClassFiles.put(className, javaClassFile);
            return javaClassFile;
        }

        public JavaMemClassFile getCachedMemClass(String fullClassName) {
            return cachedMemJavaClassFiles.get(fullClassName);
        }

    }

    public static class MemClassLoader extends ClassLoader {

        private final MemFileManager memFileManager;

        public MemClassLoader(MemFileManager memFileManager) {
            super(null);
            this.memFileManager = memFileManager;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (memFileManager.getCachedMemClass(name) != null) {
                return loadFromMem(name);
            }
            return Thread.currentThread().getContextClassLoader().loadClass(name);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (memFileManager.getCachedMemClass(name) == null) {
                return super.loadClass(name);
            }
            return findClass(name);
        }

        private Class<?> loadFromMem(String name) throws ClassNotFoundException {
            JavaMemClassFile memClassFile = memFileManager.getCachedMemClass(name);
            if (memClassFile == null) {
                log.error("memClassLoader can not find class [{}] in mem!", name);
                throw new ClassNotFoundException();
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) memClassFile.openOutputStream();
                byte[] bytes = byteArrayOutputStream.toByteArray();
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                log.error("memClassFile read byte error! className={}", name, e);
            }
            return null;
        }

    }



    public static interface Calculator {
        void func();
    }

}
