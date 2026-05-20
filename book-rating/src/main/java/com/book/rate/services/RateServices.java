package com.book.rate.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Rate> getRateById(Integer id) {
        Optional<RateModel> list = rateRepositery.findById(id);
        Optional<Rate> bookDtoOptional = list.map(rateMapper::toRate);
        return bookDtoOptional;
    }

    public List<Rate> getBookComments(Integer bookId) {
        List<RateModel> books = rateRepositery.findByLibroId(bookId);
        List<Rate> bookDtoOptional = books.stream().map(rateMapper::toRate).toList();
        return bookDtoOptional;
    }

    public List<Rate> getUserComments(Integer userId) {
        List<RateModel> rates = rateRepositery.findByUtenteId(userId);
        return rates.stream().map(rateMapper::toRate).toList();
    }

    public Rate createComment(RateModel rateModel) {
        RateModel savedRate = rateRepositery.save(rateModel);
        return rateMapper.toRate(savedRate);
    }

    public List<Rate> getAllComments() {
        List<RateModel> rates = rateRepositery.findAll();
        List<Rate> bookDtoOptional = rates.stream().map(rateMapper::toRate).toList();
        return bookDtoOptional;
    }

    public Boolean getRateDelete(Integer id){
        int DeleteRate = rateRepositery.deleteByIdNative(id);
        if (DeleteRate == 0) {
            return false;
        }
        return true;
    }
}
