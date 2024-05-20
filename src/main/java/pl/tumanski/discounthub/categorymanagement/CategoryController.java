package pl.tumanski.discounthub.categorymanagement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.tumanski.discounthub.categorymanagement.model.dto.CategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.dto.CreateCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.dto.PatchCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;
import pl.tumanski.discounthub.exception.ErrorResponse;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/category")
class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(operationId = "createCategory", summary = "Create a Category", tags = {"Category"},
            description = "Service used to create a new category in the system.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryDto.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "415", description = "Unsupported Media Type", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        Category category = categoryService.create(createCategoryDto);
        CategoryDto categoryDto = CategoryMapper.INSTANCE.toCategoryDto(category);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @Operation(operationId = "getCategoryById", summary = "Retrieve a Category by Id", tags = {"Category"},
            description = "Service used to retrieve a certain category from the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryDto.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> getCategory(@PathVariable @Valid @NotNull Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDto categoryDto = CategoryMapper.INSTANCE.toCategoryDto(category);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @Operation(operationId = "getAllCategories", summary = "Retrieve all Categories", tags = {"Category"},
            description = "Service used to retrieve all categories from the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class))
                    )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> category = categoryService.getAllCategories();
        List<CategoryDto> categoryDtos = category.stream()
                .map(CategoryMapper.INSTANCE::toCategoryDto)
                .toList();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }


    @Operation(operationId = "updateCategory", summary = "Update a Category by Id", tags = {"Category"},
            description = "Service used to update a certain category from the system.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryDto.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable @Valid @NotNull Long id,
                                                      @RequestBody @Valid PatchCategoryDto patchCategoryDto) {
        Category category = categoryService.patchCategory(id, patchCategoryDto);
        CategoryDto categoryDto = CategoryMapper.INSTANCE.toCategoryDto(category);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @Operation(operationId = "deleteCategory", summary = "Delete a Category", tags = {"Category"},
            description = "Service used to delete a category from the system. Deletes related posts.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
            }
    )
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable @Valid @NotNull Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
