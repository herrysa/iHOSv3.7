package com.huge.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.datacollection.dao.DataCollectionTaskStepDao;
import com.huge.ihos.system.datacollection.model.DataCollectionTaskStep;

public class DataCollectionTaskStepDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DataCollectionTaskStepDao dataCollectionTaskStepDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDataCollectionTaskStep() {
        DataCollectionTaskStep dataCollectionTaskStep = new DataCollectionTaskStep();

        // enter all required fields
        dataCollectionTaskStep.setExecOrder( 1179617107 );
        dataCollectionTaskStep.setExecSql( "KuByRhObUsBrTyZhVdIiNhSnZzCmSoNzKaChPsBrJwLgHyCrFdTmAhIfFyWoUsPfSlJyXeFnEkDwOeYyCiCyVkOoHuErIzWaKzUzHgWbRiFoHhZhNuNxOdZmKoRdQwLdRpDuUsYtExArSfViSgAgWuTmZdTmZzGwBjLlUwQeXuCgBlLjMpOnIvDtNsOdVvObLqSrFfPzQqXqZkWwEzIqGuOpTlQcZuNfNnPxIjNvLuDjShZeMrDiAbRaZjVaZnNyLpTnKcYnNoAkAyPuApFhIoWoQqHjDpYpUzZlWxEnNgDgOaUmQxEgCrTiGwTbGfYeRoBbNoQuBaHxSpNtHtIjHtFlLvDzCjZoGkMeHyKnJgRnHqNwEoDcEiKrImHkWcAtFoPgXiZpJfJqSgGqVhAaSeLcEzDqXhLuXaBfBqIyVpZkLqJxXcEaIdSjBgYgQdTkLaIcQfBgVxOnQsExUbBtXmZzHnWcCqCiZmPdKqDaOlWtYsRfSoTj" );
        dataCollectionTaskStep.setIsUsed( Boolean.FALSE );
        dataCollectionTaskStep.setStepName( "TjMzMlWbDpPsQrZbUwLrIcRjSfNqZkRlJqJqJbNnLjXqEiVuXs" );
        dataCollectionTaskStep.setStepType( "ZfQbPiIhGrRoRbXzHsLiPrJrNgHwHcZjDsVuXxZdEwHyGlEwKqGyNmFqQuCuHtUjAlUkVtIkYrVpAoAlJiSxClXgXzOnPwQvObViVwVrSmCcMzKaZwRaSdNsXsRjEiIpEbYwMzBeQzHzZfOiNoSgNkNnJzOsXuNeJpMlGgInEpRwCbNmZnLhDlWjCdSoGrIkBgPuSdQkFhUjXhOwYbRpGmNpTbXfRkBuLmJpClTmZpPmWrRqFoCnXiJsGaAiTcL" );

        log.debug( "adding dataCollectionTaskStep..." );
        dataCollectionTaskStep = dataCollectionTaskStepDao.save( dataCollectionTaskStep );

        dataCollectionTaskStep = dataCollectionTaskStepDao.get( dataCollectionTaskStep.getStepId() );

        assertNotNull( dataCollectionTaskStep.getStepId() );

        log.debug( "removing dataCollectionTaskStep..." );

        dataCollectionTaskStepDao.remove( dataCollectionTaskStep.getStepId() );

        // should throw DataAccessException 
        dataCollectionTaskStepDao.get( dataCollectionTaskStep.getStepId() );
    }

  //  @Test
    public void testGetByDefineId() {
        List list = dataCollectionTaskStepDao.getAllByDefineId( -1l );

        for ( Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            DataCollectionTaskStep object = (DataCollectionTaskStep) iterator.next();
            System.out.println( object.getExecOrder() );

        }
        assertTrue( list.size() > 0 );
    }
}