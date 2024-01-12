package org.zerock.mallapi.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class CustomS3Util {

  @Value("${spring.cloud.aws.s3.bucket}")
  private String bucket;

  private final S3Client s3Client;

  public void uploadFiles(List<Path> filePaths, boolean delFlag) {
    if (filePaths == null || filePaths.isEmpty()) {
      return;
    }

    for (Path filePath : filePaths) {
      PutObjectRequest request = PutObjectRequest.builder().bucket(bucket).key(filePath.toFile().getName())
          .build();

      s3Client.putObject(request, filePath);

      if (delFlag) {
        try {
          Files.delete(filePath);
        } catch (IOException e) {
          throw new RuntimeException(e.getMessage());
        }
      }
    }
  }

  public void deleteFiles(List<Path> filePaths) {
    if (filePaths == null || filePaths.isEmpty()) {
      return;
    }

    for (Path filePath : filePaths) {
      DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucket)
          .key(filePath.toFile().getName())
          .build();

      s3Client.deleteObject(deleteObjectRequest);
    }
  }
}
