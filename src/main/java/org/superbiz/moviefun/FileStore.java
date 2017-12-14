package org.superbiz.moviefun;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Optional;

public class FileStore implements BlobStore {

    private AmazonS3Client s3Client;
    private String s3BucketName;

    public FileStore(AmazonS3Client s3Client, String s3BucketName) {
        this.s3Client = s3Client;
        this.s3BucketName = s3BucketName;
    }




    @Override
    public void put(Blob blob) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();

        this.s3Client.putObject(this.s3BucketName, blob.name, blob.inputStream, metadata);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        Blob blob = new Blob(name, this.s3Client.getObject(this.s3BucketName, name).getObjectContent(), this.s3Client.getObject(this.s3BucketName, name).getObjectMetadata().getContentType());
        System.out.println();
        return Optional.of(blob);
    }
}
