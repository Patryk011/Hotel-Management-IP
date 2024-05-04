package org.example.hotelmanagementip.controller;

import org.example.hotelmanagementip.dto.CustomerDTO;
import org.example.hotelmanagementip.dto.PaymentDTO;
import org.example.hotelmanagementip.dto.ReservationDTO;
import org.example.hotelmanagementip.services.CustomerService;
import org.example.hotelmanagementip.services.PaymentService;
import org.example.hotelmanagementip.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {


    private final CustomerService customerService;

    private final ReservationService reservationService;

    private final PaymentService paymentService;




    @Autowired
    public CustomerController(CustomerService customerService, ReservationService reservationService, PaymentService paymentService) {
        this.customerService = customerService;
        this.reservationService = reservationService;
        this.paymentService = paymentService;
    }

    @GetMapping("/all")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAllCustomers();
    }
    @GetMapping("/{id}/reservations")
    public List<ReservationDTO> findByCustomerId(@PathVariable Long id) {
        return reservationService.findByCustomerId(id);
    }

    @GetMapping("/{id}/payments")
    public List<PaymentDTO> findPaymentByCustomerId(@PathVariable Long id) {
        List<ReservationDTO> reservations = reservationService.findByCustomerId(id);
        List<PaymentDTO> payments = new ArrayList<>();

        for (ReservationDTO reservation : reservations) {
            List<PaymentDTO> reservationPayments = paymentService.findPaymentsByReservationId(reservation.getId());
            payments.addAll(reservationPayments);
        }

        return payments;
    }


    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }


    @GetMapping("/filter")
    public List<CustomerDTO> getCustomersByEmail(@RequestParam String email) {
        return customerService.findCustomersByEmail(email);
    }




    @PostMapping
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
