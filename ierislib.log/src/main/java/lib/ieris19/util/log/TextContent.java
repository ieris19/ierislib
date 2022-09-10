package lib.ieris19.util.log;

/**
 * A functional interface for implementing formatting lambdas
 */
@FunctionalInterface
public interface TextContent {
	String getText(String[] args);
}
