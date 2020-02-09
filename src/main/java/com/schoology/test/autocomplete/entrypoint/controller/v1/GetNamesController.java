package com.schoology.test.autocomplete.entrypoint.controller.v1;

import com.schoology.test.autocomplete.core.usecase.UseCase;
import com.schoology.test.autocomplete.core.usecase.argument.GetNamesArgument;
import com.schoology.test.autocomplete.core.usecase.result.GetNameResult;
import com.schoology.test.autocomplete.entrypoint.controller.v1.response.EntrypointResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetNamesController {

    private final UseCase<GetNameResult, GetNamesArgument> getNamesUseCase;

    public GetNamesController(UseCase<GetNameResult, GetNamesArgument> getNamesUseCase) {
        this.getNamesUseCase = getNamesUseCase;
    }

    @GetMapping(ApiVersion.V1_PATH + "users/names")
    public ResponseEntity<EntrypointResponse<List<String>>> getNames(@RequestParam(name = "nameFilter", required = false, defaultValue = "") String nameFilter) {
        GetNameResult result = getNamesUseCase.execute(GetNamesArgument.of(nameFilter));

        return ResponseEntity.ok(EntrypointResponse.of(result.getNames()));
    }
}
