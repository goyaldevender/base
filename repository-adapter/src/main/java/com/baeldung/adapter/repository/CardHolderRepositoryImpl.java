package com.baeldung.adapter.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.baeldung.adapter.repository.utils.RepositoryUtils;
import com.baeldung.exception.CardHolderNotFoundException;
import com.baeldung.domain.CardHolder;
import com.baeldung.domain.CardCategory;
import com.baeldung.repository.CardHolderRepository;

public class CardHolderRepositoryImpl implements CardHolderRepository {

    private Map<Integer, CardHolder> dataStore = new HashMap<>();

    public CardHolder CreateCardHolder(CardHolder cardholder) {

        cardholder.setCardHolderId(RepositoryUtils.getPrimaryKey());
        cardholder.setCreditCardLimit(5000);
        cardholder.setStatus(CardCategory.BUDGET);

        dataStore.putIfAbsent(cardholder.getCardHolderId(), cardholder);

        return cardholder;
    }

    public CardHolder UpdateCardHolder(CardHolder cardHolder) throws CardHolderNotFoundException {

        if (!dataStore.containsKey(cardHolder.getCardHolderId())) {
            throw new CardHolderNotFoundException("Customer " + cardHolder.getCardHolderId() + "can't be found");
        }

        dataStore.put(cardHolder.getCardHolderId(), cardHolder);
        return cardHolder;

    }

    @Override
    public List<CardHolder> findAll() {
        List<CardHolder> all = new ArrayList<>();
        dataStore.values()
            .stream()
            .forEach(c -> all.add(c));
        return all;
    }

    @Override
    public Optional<CardHolder> findCardHolderById(int cardHolderId) {

        Optional<CardHolder> opt = Optional.empty();

        if (this.dataStore.containsKey(cardHolderId)) {
            opt = Optional.of(dataStore.get(cardHolderId));
        }
        return opt;
    }

}
