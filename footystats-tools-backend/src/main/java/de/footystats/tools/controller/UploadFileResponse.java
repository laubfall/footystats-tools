package de.footystats.tools.controller;

public record UploadFileResponse (
    String fileName,
    String fileType,
    long size
){};
