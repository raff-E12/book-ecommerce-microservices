package com.book.rate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.rate.repository.BookRepositery;
import com.book.rate.repository.CheckRepository;
import com.book.rate.repository.RateRepositery;

import java.util.List;
import java.util.Optional;

import com.book.rate.dto.Book;
import com.book.rate.dto.Rate;
import com.book.rate.maps.RatingMapping;
import com.book.rate.models.RateModel;

@Service
public class RateServices {
    
    @Autowired
    private RateRepositery rateRepositery;

    @Autowired
    private BookRepositery bookRepositery;

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private RatingMapping rateMapper;

    @Transactional(readOnly = true)
    public Optional<Rate> getRateById(Integer id) {
        Optional<RateModel> list = rateRepositery.findById(id);
        return list.map(rateMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Rate> getBookComments(Integer bookId) {
        List<RateModel> books = rateRepositery.findByLibroId(bookId);
        return books.stream().map(rateMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<Rate> getUserComments(Integer userId) {
        List<RateModel> rates = rateRepositery.findByUtenteId(userId);
        return rates.stream().map(rateMapper::toDto).toList();
    }

    @Transactional
    public Rate createComment(RateModel rateModel) {
        RateModel savedRate = rateRepositery.save(rateModel);
        return rateMapper.toDto(savedRate);
    }

    @Transactional(readOnly = true)
    public List<Rate> getAllComments() {
        List<RateModel> rates = rateRepositery.findAll();
        return rates.stream().map(rateMapper::toDto).toList();
    }

    @Transactional
    public Boolean getRateDelete(Integer id) {
        int deleted = rateRepositery.deleteByIdNative(id);
        return deleted != 0;
    }
}