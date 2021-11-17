package com.zuehlke.testing.extension;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.lang.reflect.Parameter;

public class PersonResourceExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

	private static final String PERSON_RESOURCE = "personResource";
	private final Namespace namespace = Namespace.create("Zuehlke", "Testing", "Workshop");

	@Override
	public void beforeEach(ExtensionContext context) {
		getStore(context).put(PERSON_RESOURCE, new PersonResource());
	}

	@Override
	public void afterEach(ExtensionContext context) {
		PersonResource people = (PersonResource) getStore(context).get(PERSON_RESOURCE);
		people.cleanUp();
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		Parameter parameter = parameterContext.getParameter();
		return PersonResource.class.equals(parameter.getType());
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return getStore(extensionContext).get(PERSON_RESOURCE);
	}

	private Store getStore(ExtensionContext context) {
		return context.getStore(namespace);
	}
}



