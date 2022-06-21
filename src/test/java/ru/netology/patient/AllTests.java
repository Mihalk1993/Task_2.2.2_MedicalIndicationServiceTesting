package ru.netology.patient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;


public class AllTests {

    /*
    Timur Anvartdinov, [12.04.2022 14:11]
смотрите, мы тестируем класс MedicalServiceImpl.
1) идем в этот класс и видим два поля patientInfoRepository и alertService
2) смотрим, где они используются
3) видим, что patientInfoRepository используется в методе getPatientInfo
и используется он так - patientInfoRepository.getById(patientId). Мокируем это поведение
4) alertService используется в checkBloodPressure и checkTemperature и используется так - alertService.send(message).
Мокируем это поведение.
*/

    @Test
    public void checkBloodPressureTest() {

        PatientInfo patientInfo = new PatientInfo(
                "123", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))
        );
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

//      Mockito
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(patientInfoRepository.getById("123")).thenReturn(patientInfo);

        SendAlertService alertService = Mockito.mock(SendAlertService.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(alertService).send(Mockito.any(String.class));
//      arrange
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
//      act
        medicalService.checkBloodPressure(patientInfo.getId(), new BloodPressure(120,120));
//      assert
        Assertions.assertEquals(message, valueCaptor.getValue());

    }

    @Test
    public void checkTemperatureTest() {
//      Mockito

//      arrange

//      act

//      assert

    }


}
