package br.com.casadocodigo.loja.servlets;

import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.casadocodigo.loja.infra.FileSaver;

@WebServlet("/file/*")
public class FileServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) 
                throws ServletException, IOException {
        String path = req.getRequestURI().split("/file")[1];// pegamos os dados a direta da URI, file/[1]

        Path source = Paths.get(FileSaver.SERVER_PATH + "/" + path); // Pegamos o nosso camiminho la nosso FileSaver que é static final
        FileNameMap fileNameMap = URLConnection.getFileNameMap(); // conectamos na URI e pegamos o contentTyper
        String contentType = fileNameMap.getContentTypeFor("file:"+source); // buscamos o arquivo no protocolo File, ou seja, arquivos

        res.reset();// resetamos os response, pois o JSF pode criar alguma coisa em uma de suas 7 fases
        res.setContentType(contentType); // devemos setar nosso contentTyper pois o navegador trabalha de forma diferente a cada configuyração;
        res.setHeader("Content-Length", String.valueOf(Files.size(source)));// setamos o tamanho, pelo tamaho do arquivo
        res.setHeader("Content-Disposition", 
                "filename=\""+source.getFileName().toString() + "\"");// setamos o nome do arquivo, para o navegador possa baixar o arquivo
        FileSaver.transfer(source, res.getOutputStream()); // encapsulamos a lógica de transferencia em nosso FileSaver
    }

}
