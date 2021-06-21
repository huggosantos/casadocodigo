package br.com.casadocodigo.loja.infra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;

import javax.servlet.http.Part;

public class FileSaver {

	public static final String SERVER_PATH = "C:/Users/huggo/Documents/Projetos/casadocodigo/";

	public String write(Part arquivo, String path) {
		String relativePath = path + "/" + arquivo.getSubmittedFileName();
		try {
			arquivo.write(SERVER_PATH + "/" + relativePath);
			return relativePath;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Souce: nosso caminho, e OutputStream nossa saída.
	public static void transfer(Path source, OutputStream outputStream) {
		try {
			FileInputStream input = new FileInputStream(source.toFile()); /* Devemos fazer o input do arquivo em nosso server*/ 

			try (ReadableByteChannel inputChannel = Channels.newChannel(input); /* Criação de canal de entrada para
																				   transferir os arquivos, então usamos
																				   o try para fechar nossa conexão
																				   automaticamente */ 
					WritableByteChannel outputChannel = Channels.newChannel(outputStream)/* Canal de saida */) {
				ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);/*Nosso buffer para gardar nosso arquivo na transferencia, tb setamos o tammho*/

				while (inputChannel.read(buffer) != -1) {/*Laço de repetição para gravar*/
					buffer.flip(); // setar os byts para zero, obrigatório
					outputChannel.write(buffer);// escrever no buffer
					buffer.clear();// limpar buffer
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}
}
