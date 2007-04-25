/* 
 * Copyright 2005 Paul Hinds
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tp23.antinstaller.renderer.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.tp23.antinstaller.InstallException;
import org.tp23.antinstaller.page.Page;
import org.tp23.antinstaller.page.SplashPage;

public class SplashPageRenderer extends TextPageRenderer {
	public SplashPageRenderer() {
	}
	public boolean renderPage(Page page) throws InstallException {
		if (page instanceof SplashPage) {
			SplashPage sPage = (SplashPage) page;
			return renderSplashPage(sPage);
		}
		else {
			throw new InstallException("Wrong Renderer in SplashPageRenderer.renderPage");
		}
	}
	private boolean renderSplashPage(SplashPage page) throws InstallException {
		try {
			printHeader(page);
			out.println();
			out.println(page.getAltText());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			reader.read();
			return true;
		}
		catch (IOException ex) {
			throw new InstallException("IOException");
		}
	}
}
