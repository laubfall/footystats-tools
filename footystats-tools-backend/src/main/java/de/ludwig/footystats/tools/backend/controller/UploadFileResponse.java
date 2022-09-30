package de.ludwig.footystats.tools.backend.controller;

public record UploadFileResponse (
    String fileName,
    String fileType,
    long size
){};
