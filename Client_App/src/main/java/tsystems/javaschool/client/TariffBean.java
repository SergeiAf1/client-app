package tsystems.javaschool.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import tsystems.javaschool.client.model.Tariff;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Log4j
@Singleton
@NoArgsConstructor
@Named
public class TariffBean {

    @Inject
    private Receiver receiver;

    private final Loader loader = Loader.getInstance();

    @Getter
    @Setter
    private List<Tariff> tariffs;

    public void loadTariffList() {
        tariffs = loader.getTariffs();
    }

    @PostConstruct
    private void init() {
        try {
            receiver.getMessage();
        } catch (IOException | TimeoutException e) {
            log.warn("Can't get message");
        }
        loadTariffList();
    }

    @PreDestroy
    private void destroy() {
        try {
            receiver.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
