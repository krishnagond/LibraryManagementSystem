package com.backendMarch.demo.Service;

import com.backendMarch.demo.DTO.IssueBookRequestDto;
import com.backendMarch.demo.DTO.IssueBookResponseDto;
import com.backendMarch.demo.Entity.Book;
import com.backendMarch.demo.Entity.LibraryCard;
import com.backendMarch.demo.Entity.Transaction;
import com.backendMarch.demo.Enum.CardStatus;
import com.backendMarch.demo.Enum.TransactionStatus;
import com.backendMarch.demo.Repository.BookRepository;
import com.backendMarch.demo.Repository.CardRepository;
import com.backendMarch.demo.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    private JavaMailSender emailSender;
    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {
        // create transaction object
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        // 1 step
        LibraryCard card;
        try{
            card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card id");
        }
        Book book;
        try {
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);

            transaction.setMessage("Invalid book id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid book id");
        }
        transaction.setBook(book);
        transaction.setCard(card);
        //both book and card is valid
        if (card.getStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);

            transaction.setMessage("Your card is not activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not activated");
        }
        if (book.isIssued() == true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);

            transaction.setMessage("Sorry! Book is already issued.");
            transactionRepository.save(transaction);
            throw new Exception("Sorry! Book is already issued.");
        }
        // I can issue the book
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successful");
        book.setIssued(true);
        book.setCard(card);
        book.getTransaction().add(transaction);
        card.getTransactionList().add(transaction);
        card.getBooksIssued().add(book);

       cardRepository.save(card);

       // prepare response dto
        IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();
        issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        issueBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);
        issueBookResponseDto.setBookName(book.getTitle());


        String text = "Congrats !!" + " " + card.getStudent().getName() + " " + "You have been issued" + " " + book.getTitle()+" "+"book.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lmsmarch108@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("issue Book Notification");
        message.setText(text);
        emailSender.send(message);

        return issueBookResponseDto;
    }
}
