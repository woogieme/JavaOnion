package webserver;


import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.Arrays;

import main.java.util.IOUtils;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.UtilClass;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private UtilClass utilClass = new UtilClass();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            //1단계
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            String line =br.readLine();
            log.debug(line);

            if(line==null){
                return;
            }

            //2단계
            String[] tokens = line.split(" ");

            String parmeterType = tokens[0];

            int len =0;

            while(!line.equals("")){
                line=br.readLine();
                String[] tok = line.split(" ");

                if(tok[0].equals("Content-Length:")){
                    String [] to = line.split(" ");
                    len = Integer.parseInt(to[1]);
                }
                log.debug(line);
            }


            System.out.println(tokens[1]);
            System.out.println(parmeterType);
            if(tokens[1].startsWith("/user/create")){
                if(parmeterType.equals("get")){

                    //요구사항 2단계 GET 방식으로 회원가입하기
                    int index =tokens[1].indexOf("?");
                    String queryString = tokens[1].substring(index+1);
                    User user = User.createUser(queryString);
                    log.debug(user.toString());
                }else if(parmeterType.equals("POST")){

                    //요구사항 3단계 POST 방식으로 회원가입하기
                    String query = IOUtils.readData(br,len);
                    User user = User.createUser(query);
                    log.debug(user.toString());
                }

            }else {

                DataOutputStream dos = new DataOutputStream(out);

                //byte[] body = "Hello World".getBytes();
                //3단계
                byte[] body = Files.readAllBytes(new File("./webapp" + tokens[1]).toPath());

                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
