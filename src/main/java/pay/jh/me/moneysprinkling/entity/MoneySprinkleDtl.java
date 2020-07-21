package pay.jh.me.moneysprinkling.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "money_sprinkle_dtl")
public class MoneySprinkleDtl {
    public MoneySprinkleDtl(@NonNull String token) {
        this.token = token;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moneySprinkleDtlSeq;

    @NonNull
    @ManyToOne(targetEntity = MoneySprinkle.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "token", insertable = false, updatable = false)
    private MoneySprinkle moneySprinkle;

    @NonNull
    private String token;

    @NonNull
    private double amount;

    @NonNull
    private String receiver;
    private LocalDateTime receivedAt;
}
