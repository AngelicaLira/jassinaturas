package sdk.jassinaturas.communicators;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import sdk.jassinaturas.clients.attributes.Authentication;
import sdk.jassinaturas.feign.BasicAuthRequestInterceptor;
import sdk.jassinaturas.feign.FixedHeadersInterceptor;

public class SandboxCommunicator implements Communicator {

    public <T> T build(final Class<T> clazz, final Authentication authentication) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return Feign
                .builder()
                .decoder(new GsonDecoder(gson))
                .encoder(new GsonEncoder(gson))
                .errorDecoder(new ErrorHandler())
                .requestInterceptor(
                        new BasicAuthRequestInterceptor(authentication.getToken(), authentication.getSecret()))
                .requestInterceptor(new FixedHeadersInterceptor())
                .target(clazz, "https://sandbox.moip.com.br/assinaturas/v1");
    }
}
