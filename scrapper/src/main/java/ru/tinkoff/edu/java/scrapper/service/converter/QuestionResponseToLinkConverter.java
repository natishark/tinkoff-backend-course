package ru.tinkoff.edu.java.scrapper.service.converter;

import org.springframework.core.convert.converter.Converter;
import ru.tinkoff.edu.java.scrapper.client.stackoverflow.dto.QuestionResponse;
import ru.tinkoff.edu.java.scrapper.domain.dto.Link;

import java.sql.Timestamp;

public class QuestionResponseToLinkConverter implements Converter<QuestionResponse, Link> {

    @Override
    public Link convert(QuestionResponse response) {
        var item = response.items().get(0);
        return new Link()
                .setUrl(item.link().toString())
                .setUpdatedAt(Timestamp.valueOf(item.lastActivityDate().toLocalDateTime()))
                .setAnswerCount(item.answerCount());
    }
}
