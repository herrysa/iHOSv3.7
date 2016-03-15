package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;
import com.huge.ihos.system.datacollection.service.impl.DataCollectionTaskStepManagerImpl;

public class DataCollectionTaskStepManagerImplTest
    extends BaseManagerMockTestCase {
    private DataCollectionTaskStepManagerImpl manager = null;

    private DataCollectionTaskStepDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DataCollectionTaskStepDao.class );
        manager = new DataCollectionTaskStepManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDataCollectionTaskStep() {
        log.debug( "testing get..." );

        final Long stepId = 7L;
        final DataCollectionTaskStep dataCollectionTaskStep = new DataCollectionTaskStep();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( stepId ) ) );
                will( returnValue( dataCollectionTaskStep ) );
            }
        } );

        DataCollectionTaskStep result = manager.get( stepId );
        assertSame( dataCollectionTaskStep, result );
    }

    @Test
    public void testGetDataCollectionTaskSteps() {
        log.debug( "testing getAll..." );

        final List dataCollectionTaskSteps = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( dataCollectionTaskSteps ) );
            }
        } );

        List result = manager.getAll();
        assertSame( dataCollectionTaskSteps, result );
    }

    @Test
    public void testSaveDataCollectionTaskStep() {
        log.debug( "testing save..." );

        final DataCollectionTaskStep dataCollectionTaskStep = new DataCollectionTaskStep();
        // enter all required fields
        dataCollectionTaskStep.setExecOrder( 691499179 );
        dataCollectionTaskStep.setExecSql( "DeCbWmTxSaLjZwWwVbOyPhFoCxSwCgMgSpPxEaXdOgMgRwQgPuCvZkNfXvKiSdZcHcAaOuMeLcOnHcKwZeWvQgNrTkCkLdBzSsScMgNdPfKyDjFtQwHlWaMsWmKzNyOxBcIbQbCkVjUlJgGsRpDhLsOcKwJtCuHmLoFgOwOcQzZcQwRzIvJrPkVaJsVvYbFdHySaJiYaYkKmNqMtQnExRzLjZeQoImAjWgXnCnDrQtTeXjNgSdGpYcCuSdHyXeXjWiMlYmPxWrApPwZvXaHgJjSkHiVwClOwOnYoUzYjCzHcIiCnOtYdCtLiJxVxTcHcUdYzIsJmUpDbXhVsTcQmUjTcVpYbSyWhOsCaYjNmZtUuPvFdIbBrMhBhAbJfEqXfUrUdHlRaPpIaZwPaLyPaNwExRpRhUnEyEhIeSaWoNtFcTuJnZfEoUbPhMsAdGcRzMsMxFrQmDlWhVkWpMzGeFgIuMoMgFfGoAqBsRyRdFpWfYbIsCgDa" );
        dataCollectionTaskStep.setIsUsed( Boolean.FALSE );
        dataCollectionTaskStep.setStepName( "PaPoJyQeEeHjGqUoBxDeWjAvJwPiDcQzNoMnGiJuYmVcWkPhCw" );
        dataCollectionTaskStep.setStepType( "TiUwXjPoLcMfRrQxVrJfByEgRxZrAcEnOgWdMpTfPtOtKnCtLnJsZnCqBiOsSeLhPpXhJnZnYbIhOdJiSbTlSoOnHgEaNzHvApRfTcCxUvSfZxIqYoLnEqOtWmXeSaSwNkGyBeLoOqBbHvEyTxRcToOdRyQzDwKgUhJzPzHwSjDpDtKhXfGtMjHbLyOaDkXiJqKiOrBwUfDnGnBqAnTmUjBhRxRdLeTkTjFxIxCoYwAcHvEyAuNsHuVxMyAmDlI" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( dataCollectionTaskStep ) ) );
            }
        } );

        manager.save( dataCollectionTaskStep );
    }

    @Test
    public void testRemoveDataCollectionTaskStep() {
        log.debug( "testing remove..." );

        final Long stepId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( stepId ) ) );
            }
        } );

        manager.remove( stepId );
    }
}