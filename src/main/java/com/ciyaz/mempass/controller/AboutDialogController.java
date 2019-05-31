package com.ciyaz.mempass.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AboutDialogController {

	@FXML
	private TextArea taLicense;

	@FXML
	private void initialize() {
		InputStream is = AboutDialogController.class.getClassLoader().getResourceAsStream("conf/LICENSE");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			br.close();
			isr.close();
			is.close();
			taLicense.setText(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
