package ch.unibe.scg.doodle.test;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ch.unibe.scg.doodle.typeTransport.ClassUtil;
import dummy.C;
import dummy.B;
import dummy.A;
import dummy.TypeDependencyDummy;

public class TypeDependencyTest {

	@Test
	public void testDummyDependencies() {
		Class<?>[] depencencyArray = { A.class, B.class, C.class,
				TypeDependencyDummy.class };
		Set<Class<?>> expectedDependencies = new HashSet<>();
		Collections.addAll(expectedDependencies, depencencyArray);

		assertEquals(expectedDependencies,
				ClassUtil.getThirdPartyDependencies(TypeDependencyDummy.class));
		;
	}
}
