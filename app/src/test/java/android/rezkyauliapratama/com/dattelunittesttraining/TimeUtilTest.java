package android.rezkyauliapratama.com.dattelunittesttraining;

import android.rezkyauliapratama.com.dattelunittesttraining.utils.TimeUtil;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.doThrow;

public class TimeUtilTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    SimpleDateFormat simpleDateFormatMock;


    //we will test this class
    TimeUtil SUT;

    final String dummyData = "dummyData";
    final Date dummyDate = new Date();
    final String strFriendlyDate = "10 October 2018";


    @Before
    public void setUp() throws Exception {
        SUT = new TimeUtil(simpleDateFormatMock);
        success_parse();

    }


    //naming unittest : <unitOfWork>_<stateUnderTest>_<expectedBehaviour>


    @Test
    public void getUserFriendlyDate_success_returnCorrectString() {
        success_format();
        String result = SUT.getUserFriendlyDate(dummyDate);
        Assert.assertThat(result, CoreMatchers.is(strFriendlyDate));
    }

    @Test
    public void getUserFriendlyDate_success_datePassingIntoMethod() {
        success_format();
        SUT.getUserFriendlyDate(dummyDate);

        ArgumentCaptor<Date> ac = ArgumentCaptor.forClass(Date.class);
        Mockito.verify(simpleDateFormatMock).format(ac.capture());

        Date result = ac.getValue();
        Assert.assertThat(result, CoreMatchers.is(dummyDate));
    }


    @Test
    public void convertStringToDate_error_throwException() throws Exception {
        error_parse();
        Date result = SUT.convertStringToDate(dummyData);
        Date nullDate = null;
        Assert.assertThat(result,CoreMatchers.is(nullDate));
    }

    @Test
    public void convertStringToDate_failure_returnNullValue() throws Exception {
        failure_parse();
        Date result = SUT.convertStringToDate(dummyData);
        Date nullDate = null;
        Assert.assertThat(result,CoreMatchers.is(nullDate));
    }


    @Test
    public void convertStringToDate_success_returnCorrectDate() {
        Date result = SUT.convertStringToDate(dummyData);
        Assert.assertThat(result, CoreMatchers.is(dummyDate));
    }

    @Test
    public void convertStringToDate_success_stringPassingIntoMethod() throws Exception {

        SUT.convertStringToDate(dummyData);

        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        Mockito.verify(simpleDateFormatMock).parse(ac.capture());

        String result = ac.getValue();
        Assert.assertThat(result, CoreMatchers.is(dummyData));

    }

    private void success_parse() throws Exception {
        String dummyValue = ArgumentMatchers.any(String.class);
        Mockito.when(simpleDateFormatMock.parse(dummyValue)).thenReturn(dummyDate);
    }


    private void success_format() {
        Mockito.when(simpleDateFormatMock.format(ArgumentMatchers.any(Date.class))).thenReturn(strFriendlyDate);
    }

    private void failure_parse() throws Exception {
        Mockito.when(simpleDateFormatMock.parse(ArgumentMatchers.any(String.class))).thenReturn(null);
    }

    private void error_parse() throws Exception {
        doThrow(new ParseException("abc",1))
                .when(simpleDateFormatMock).parse(ArgumentMatchers.any(String.class));

    }

}
