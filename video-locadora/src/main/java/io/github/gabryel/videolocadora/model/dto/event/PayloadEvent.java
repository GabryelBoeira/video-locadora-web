package io.github.gabryel.videolocadora.model.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PayloadEvent(
        Long rentalId,
        String customerName,
        String customerEmail,
        String cellPhone,
        String phone,
        BigDecimal totalPrice,
        LocalDateTime rentalDueAt,
        List<String> movieNames
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long rentalId;
        private String customerName;
        private String customerEmail;
        private String cellPhone;
        private String phone;
        private BigDecimal totalPrice;
        private LocalDateTime rentalDueAt;
        private List<String> movieNames;

        public Builder rentalId(Long rentalId) {
            this.rentalId = rentalId;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder customerEmail(String customerEmail) {
            this.customerEmail = customerEmail;
            return this;
        }

        public Builder cellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder rentalDueAt(LocalDateTime rentalDueAt) {
            this.rentalDueAt = rentalDueAt;
            return this;
        }

        public Builder movieNames(List<String> movieNames) {
            this.movieNames = movieNames;
            return this;
        }

        public PayloadEvent build() {
            return new PayloadEvent(
                    rentalId,
                    customerName,
                    customerEmail,
                    cellPhone,
                    phone,
                    totalPrice,
                    rentalDueAt,
                    movieNames
            );
        }
    }
}