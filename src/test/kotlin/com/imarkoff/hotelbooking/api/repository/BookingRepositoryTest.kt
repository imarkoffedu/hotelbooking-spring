package com.imarkoff.hotelbooking.api.repository

import com.imarkoff.hotelbooking.api.service.bookingservice.getMockBooking
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*
import kotlin.test.Test

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private lateinit var bookingRepository: BookingRepository

    @Test
    fun `testFindByUserId should return bookings for user`() {
        val userId = UUID.randomUUID()
        val booking1 = getMockBooking().copy(userId = userId)
        val booking2 = getMockBooking()
        bookingRepository.save(booking1)
        bookingRepository.save(booking2)

        val result = bookingRepository.findByUserId(userId)

        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(booking1)
    }

    @Test
    fun `testFindByUserId should return empty list for non-existing user`() {
        val userId = UUID.randomUUID()
        val booking1 = getMockBooking()
        val booking2 = getMockBooking()
        bookingRepository.save(booking1)
        bookingRepository.save(booking2)

        val result = bookingRepository.findByUserId(userId)

        assertThat(result).isEmpty()
    }

    @Test
    fun `testFindByRoomNumber should return bookings for room number`() {
        val roomNumber = 102
        val booking1 = getMockBooking().copy(roomNumber = roomNumber)
        val booking2 = getMockBooking()
        bookingRepository.save(booking1)
        bookingRepository.save(booking2)

        val result = bookingRepository.findByRoomNumber(roomNumber)

        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(booking1)
    }

    @Test
    fun `testFindByRoomNumber should return empty list for non-existing room number`() {
        val roomNumber = 777
        val booking1 = getMockBooking()
        val booking2 = getMockBooking()
        bookingRepository.save(booking1)
        bookingRepository.save(booking2)

        val result = bookingRepository.findByRoomNumber(roomNumber)

        assertThat(result).isEmpty()
    }
}