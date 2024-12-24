package br.com.lab.rule;

import br.com.lab.exception.RegiaoInvalidaException;
import com.amazonaws.regions.Regions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidaRegiaoRule {

    private static final Logger log = LoggerFactory.getLogger(ValidaRegiaoRule.class);

    public Regions validar(String regionName) {
        try {
            return Regions.valueOf(regionName);
        } catch (IllegalArgumentException e) {
            log.error("Erro ao validar região '{}' {}", regionName, e.getMessage());
            throw new RegiaoInvalidaException("A Região %s não é válida.".formatted(regionName), e);
        }
    }
}
