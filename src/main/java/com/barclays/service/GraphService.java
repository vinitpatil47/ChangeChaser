package com.barclays.service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.azure.identity.DeviceCodeInfo;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;

@Service
public class GraphService {

    private DeviceCodeCredential deviceCodeCredential;
    private GraphServiceClient<Request> graphClient;
    
    @Value("${app.clientId}")
    private String clientId;
    @Value("${app.authTenant}")
    private String authTenantId;
    @Value("${app.graphUserScopes}")
    private String graphUserScopes;
    
    public void initializeGraphForUserAuth(Consumer<DeviceCodeInfo> challenge) throws Exception {

        final String clientId = this.clientId;
        final String authTenantId = this.authTenantId;
        final List<String> graphUserScopes = Arrays.asList(this.graphUserScopes.split(","));

        this.deviceCodeCredential = new DeviceCodeCredentialBuilder()
            .clientId(clientId)
            .tenantId(authTenantId)
            .challengeConsumer(challenge)
            .build();

        final TokenCredentialAuthProvider authProvider =
            new TokenCredentialAuthProvider(graphUserScopes, this.deviceCodeCredential);

        this.graphClient = GraphServiceClient.builder()
            .authenticationProvider(authProvider)
            .buildClient();
    }

	public GraphServiceClient<Request> getGraphClient() {
		return graphClient;
	}

	public void setGraphClient(GraphServiceClient<Request> graphClient) {
		this.graphClient = graphClient;
	}
    
}
