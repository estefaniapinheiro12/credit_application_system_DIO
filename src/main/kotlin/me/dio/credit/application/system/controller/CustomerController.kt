package me.dio.credit.application.system.controller

import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.service.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CustomerController(private val service: CustomerService) {

    @PostMapping
    fun save(@RequestBody customer: Customer): ResponseEntity<Customer> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.save(customer))

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Customer> =
        ResponseEntity.ok(service.findById(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody customer: Customer): ResponseEntity<Customer> {
        val updated = service.save(customer.copy(id = id))
        return ResponseEntity.ok(updated)
    }
}