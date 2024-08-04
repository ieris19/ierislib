package com.ieris19.lib.util.log.custom;

/**
 * A functional interface for implementing formatting lambdas
 */
@FunctionalInterface
public interface TextField {
    /**
     * A method to be used in lambda expressions to return a string based on an arbitrarily big string arguments
     *
     * @param args the array of arguments to be used in the lambda expression
     * @return the text field
     */
    String getText(String... args);
}
