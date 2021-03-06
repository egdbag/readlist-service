package com.readlist.service.core.storage.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("articles")
public class ArticleSchema {
    @Id
    private Integer id;
    private String title;
    private String url;
    private Instant ts;
    @Column("user_id")
    private Integer userId;
}
