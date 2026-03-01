package br.me.dio;

import br.me.dio.model.CpfRequest;
import br.me.dio.model.CpfResponse;
import com.google.gson.Gson;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import br.me.dio.service.CPFValidate;

public class CpfFunction {
@FunctionName("validateCpf")
public HttpResponseMessage run(
@HttpTrigger(
        name = "req",
        methods = {HttpMethod.POST},
        authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<Optional<String>> request,
final ExecutionContext context) {

        context.getLogger().info("Processing CPF validation...");

        String requestBody = request.getBody().orElse(null);

        if (requestBody == null) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body("{\"error\": \"Request body is missing\"}")
                        .build();
        }

        Gson gson = new Gson();
        CpfRequest cpfRequest = gson.fromJson(requestBody, CpfRequest.class);

        if (cpfRequest.getCpf() == null || cpfRequest.getCpf().isEmpty()) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .header("Content-Type", "application/json")
                        .body("{\"error\": \"CPF must be provided\"}")
                        .build();
        }

        boolean valid = CPFValidate.isCPF(cpfRequest.getCpf());

        String mensagem = valid
                ? "O CPF é válido."
                : "O CPF não é válido.";

        Map<String, Object> response = new HashMap<>();
        response.put("cpf", cpfRequest.getCpf());
        response.put("valido", valid);
        response.put("mensagem", mensagem);

        String jsonResponse = gson.toJson(response);

        return request.createResponseBuilder(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(jsonResponse)
                .build();
}
}

/*public class CpfFunction {

    @FunctionName("validateCpf")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Validating CPF...");

        String cpf = request.getQueryParameters().get("cpf");

        if (cpf == null || cpf.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please provide a CPF in the query string")
                    .build();
        }

        boolean valid = CPFValidate.isCPF(cpf);

        CpfResponse response = new CpfResponse(cpf, valid);

        String json = new Gson().toJson(response);

        return request.createResponseBuilder(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(json)
                .build();

        *//*return request.createResponseBuilder(HttpStatus.OK)
                .body("CPF " + cpf + " is valid? " + valid)
                .build();*//*
    }
}*/
