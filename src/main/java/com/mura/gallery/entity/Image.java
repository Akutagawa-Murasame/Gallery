package com.mura.gallery.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * @author Akutagawa Murasame
 */
@Data
@AllArgsConstructor
public class Image {
    private Integer userId;
    private Date uploadDate;
    private String imagePath;
}
