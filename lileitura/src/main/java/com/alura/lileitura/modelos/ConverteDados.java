package com.alura.lileitura.modelos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados {
	private ObjectMapper mapper = new ObjectMapper();
	
	public ResultDto obterDados(String json) {
		try {
			return mapper.readValue(json, ResultDto.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
