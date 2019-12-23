package com.learning.hotcompile;

/**
 * @author xuechongyang
 */
public class HotCompileTest {

    public static void main(String[] args) {
        String sourceCode =
                "package com.learning.hotcompile;\n" +
                        "import com.learning.hotcompile.ClassMemCompiler;\n" +
                        "import java.util.Date;\n" +
                        "\n" +
                        "public class TestRule implements ClassMemCompiler.Calculator{\n" +
                        "    private Integer id;\n" +
                        "    @Override\n" +
                        "    public void func() {\n" +
                        "       \tid = 1;\n" +
                        "        System.out.println(\"id=\" + id);\n" +
                        "    }\n" +
                        "}";

        String className = "com.learning.hotcompile.TestRule";
        try {
            Class clazz = ClassMemCompiler.compileAndLoad(className, sourceCode);
            ClassMemCompiler.Calculator cal = (ClassMemCompiler.Calculator) clazz.newInstance();
            cal.func();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
