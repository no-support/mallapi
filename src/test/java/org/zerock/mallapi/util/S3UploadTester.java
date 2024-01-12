package org.zerock.mallapi.util;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class S3UploadTester {

  @Autowired
  CustomS3Util s3Util;

  @Test
  public void testUpload() {
    log.info("update test.....");
    Path filePath = new java.io.File("C:\\upload\\m1.jpg").toPath();
    List<Path> fileList = List.of(filePath);
    s3Util.uploadFiles(fileList, false);

  }
}
