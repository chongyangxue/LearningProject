package com.learning.hotcompile;

import lombok.Data;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.List;

/**
 * @author xuechongyang
 */
@Data
public class MemCompileException extends Exception {

    private String classSimpleName;

    private List<Diagnostic<? extends JavaFileObject>> diagnosticList;

    MemCompileException(String classSimpleName, List<Diagnostic<? extends JavaFileObject>> diagnosticList) {
        super();
        this.classSimpleName = classSimpleName;
        this.diagnosticList = diagnosticList;
    }
}
