/*******************************************************************************
 * Copyright (c) 2017 Skymatic UG (haftungsbeschränkt).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the accompanying LICENSE file.
 *******************************************************************************/
package org.cryptomator.common.settings;

import org.cryptomator.common.Environment;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

public class SettingsTest {

	@Test
	public void testAutoSave() {
		Environment env = Mockito.mock(Environment.class);
		@SuppressWarnings("unchecked") Consumer<Settings> changeListener = Mockito.mock(Consumer.class);

		Settings settings = new Settings(env);
		settings.setSaveCmd(changeListener);
		VaultSettings vaultSettings = VaultSettings.withRandomId();
		Mockito.verify(changeListener, Mockito.times(0)).accept(settings);

		// first change (to property):
		settings.port().set(42428);
		Mockito.verify(changeListener, Mockito.times(1)).accept(settings);

		// second change (to list):
		settings.getDirectories().add(vaultSettings);
		Mockito.verify(changeListener, Mockito.times(2)).accept(settings);

		// third change (to property of list item):
		vaultSettings.displayName().set("asd");
		Mockito.verify(changeListener, Mockito.times(3)).accept(settings);
	}

}
