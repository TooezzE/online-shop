package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    private final CommentService commentService;

    public AdController(AdService adService, CommentService commentService) {
        this.adService = adService;
        this.commentService = commentService;
    }
    @Operation(tags = "Объявления",
            summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    )
            })
    //Получаем все объявления
    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }
    @Operation(
            summary = "Добавление объявления",
            tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdDTO.class))),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized"),
    })
    //Добавляем объявление
    @PostMapping
    public ResponseEntity<AdDTO> addAd(@RequestPart(value = "properties", required = false) CreateOrUpdateAdDTO createOrUpdateAdDTO,
                                       @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(adService.addAd(createOrUpdateAdDTO, image));

    }
    @Operation(
            tags = "Объявления",
            summary = "Получение информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDTO.class))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found")})


    //Получаем информацию об объявлении
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAdInfo(@PathVariable Integer id) {
        return ResponseEntity.ok(adService.getAdInfo(id));

    }
    @Operation(
            tags = "Объявления",
            summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found")})



    //Удаление объявления
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        return ResponseEntity.ok((adService.deleteAd(id)));
    } @Operation(
            tags = "Объявления",
            summary = "Обновление информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )


    //Обновление информации в объявлении
    @PatchMapping("/{adId}")
    public ResponseEntity<AdDTO> updateAd(@PathVariable Integer adId, @RequestBody CreateOrUpdateAdDTO createOrUpdateAdDTO) {
        return ResponseEntity.ok(adService.patchAd(adId, createOrUpdateAdDTO));
    }
    @Operation(
            tags = "Объявления",
            summary = "Получение объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    )
            }
    )

    //Получение объявления авторизованного пользователя
    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsUser() {
        return ResponseEntity.ok(adService.getAllAdsByAuthor());
    }
    @Operation(
            tags = "Объявления",
            summary = "Обновление картинки объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Byte.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )

            }
    )
    //Обновление картинки объявления
    @PatchMapping("/{id}/image")
    public ResponseEntity<Void> updateAdImage(@PathVariable Integer id, @RequestPart MultipartFile image) {
        return ResponseEntity.ok(adService.patchAdImage(id, image));
    }

//Комментарии
@Operation(
        summary = "Получение комментариев объявления",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = CommentDTO.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content()
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Not Found",
                        content = @Content()
                )
        }
)
    //Получить комментарии об объявлении
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getComments(id));
    }
    @Operation(
            summary = "Добавление комментария к объявлению",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    //Добавление комментария к объявлению
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Integer id,@RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return ResponseEntity.ok(commentService.addComment(id, createOrUpdateCommentDTO));

    }
    @Operation(
            summary = "Удаление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )

    //Удаление комментария
    @DeleteMapping("{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id,@PathVariable Integer commentId) {
        return ResponseEntity.ok(commentService.deleteComment(id, commentId));

    }
    @Operation(
            summary = "Обновление комментария",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content()
                    )
            }
    )
    //Обновление комментария
    @PatchMapping("{id}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Integer id,@PathVariable Integer commentId,
                                                    @RequestBody CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        return ResponseEntity.ok(commentService.patchComment(id, commentId, createOrUpdateCommentDTO));

    }



}

