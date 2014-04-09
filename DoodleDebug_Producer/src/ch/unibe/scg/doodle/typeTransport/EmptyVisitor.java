package ch.unibe.scg.doodle.typeTransport;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * EmptyVisitor was removed with ASM 4.0 (not sure why). This one should do the
 * same as the original one.
 * 
 * @author cedric
 * 
 */
public class EmptyVisitor extends ClassVisitor {
	protected AnnotationVisitor av = new AnnotationVisitor(Opcodes.ASM4) {

		@Override
		public AnnotationVisitor visitAnnotation(String name, String desc) {
			return this;
		}

		@Override
		public AnnotationVisitor visitArray(String name) {
			return this;
		}
	};

	public EmptyVisitor() {
		super(Opcodes.ASM4);
	}

	protected MethodVisitor mv = new MethodVisitor(Opcodes.ASM4) {

		@Override
		public AnnotationVisitor visitAnnotationDefault() {
			return av;
		}

		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			return av;
		}

		@Override
		public AnnotationVisitor visitParameterAnnotation(int parameter,
				String desc, boolean visible) {
			return av;
		}
	};

	protected FieldVisitor fv = new FieldVisitor(Opcodes.ASM4) {

		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			return av;
		}
	};

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return av;
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		return fv;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		return mv;
	}
}
