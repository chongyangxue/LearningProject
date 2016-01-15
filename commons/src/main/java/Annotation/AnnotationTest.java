package Annotation;

@MyAnnotation(color="red", value="abc", array={1, 2, 3}, annotationAttr=@MetaAnnotation("world"))
public class AnnotationTest {
	@MyAnnotation("def")
	public static void main(String[] args) {
		if(AnnotationTest.class.isAnnotationPresent(MyAnnotation.class)){
			MyAnnotation annotation = 
					(MyAnnotation)AnnotationTest.class.getAnnotation(MyAnnotation.class);
			System.out.println(annotation.color());
			System.out.println(annotation.value());
			System.out.println(annotation.array().length);
			System.out.println(annotation.annotationAttr().value());
			System.out.println(annotation.clazz());
		}
	}
}
