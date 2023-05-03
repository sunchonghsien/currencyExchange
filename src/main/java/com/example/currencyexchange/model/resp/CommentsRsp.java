package com.example.currencyexchange.model.resp;

import com.example.currencyexchange.model.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CommentsRsp extends PaginationRsp {
    public List<Comments> list;
}
