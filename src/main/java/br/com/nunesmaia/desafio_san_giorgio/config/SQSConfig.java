package br.com.nunesmaia.desafio_san_giorgio.config;

import br.com.nunesmaia.desafio_san_giorgio.domain.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.regions.Region;

import java.net.URI;

@Configuration
public class SQSConfig {

    @Value("${aws.sqs.endpoint}")
    private String endpoint;

    @Value("${aws.sqs.queue-partial}")
    private String queuePartial;

    @Value("${aws.sqs.queue-total}")
    private String queueTotal;

    @Value("${aws.sqs.queue-excess}")
    private String queueExcess;

    @Value("${aws.sqs.account-id}")
    private String accountId;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(URI.create(this.endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretKey)))
                .region(Region.of(this.awsRegion))
                .build();
    }

    public String getQueueUrl(StatusEnum status) {
        String queue;

        switch (status) {
            case PARTIAL:
                queue = this.queuePartial;
                break;
            case TOTAL:
                queue = this.queueTotal;
                break;
            default:
                queue = queueExcess;
                break;
        }

        return String.format("%s/%s/%s", this.endpoint, this.accountId, queue);
    }
}
