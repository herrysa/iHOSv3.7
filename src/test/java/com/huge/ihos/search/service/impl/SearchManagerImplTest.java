package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.search.dao.SearchDao;
import com.huge.ihos.system.reportManager.search.model.Search;
import com.huge.ihos.system.reportManager.search.service.impl.SearchManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class SearchManagerImplTest
    extends BaseManagerMockTestCase {
    private SearchManagerImpl manager = null;

    private SearchDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SearchDao.class );
        manager = new SearchManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSearch() {
        log.debug( "testing get..." );

        final String searchId = "7L";
        final Search search = new Search();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( searchId ) ) );
                will( returnValue( search ) );
            }
        } );

        Search result = manager.get( searchId );
        assertSame( search, result );
    }

    @Test
    public void testGetSearches() {
        log.debug( "testing getAll..." );

        final List searches = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( searches ) );
            }
        } );

        List result = manager.getAll();
        assertSame( searches, result );
    }

   // @Test
    public void testSaveSearch() {
        log.debug( "testing save..." );

        final Search search = new Search();
        // enter all required fields
        search.setDs( "AhRoJcXiWsCx" );
        search.setEntityName( "BsDeHgTkDtXzVfYkQvFuCsQbVfXxKqRcIuQxBnVzSvRlU" );
        search.setFormAction( "MtNoHaVmWuBhIjKdBuJkNmJjDbFaPdHrMeRaHxNoMeNpShIdGqImPvObHfCjSeVdLmDpDaTyLtLrLcBuNxAaKjMnQwXdMsHdTiDtShBqFiExAkDbJeXhRsBqHyHuLeFvYaVwVrGfIxHlMsZfAjPaKg" );
        search.setFormName( "WiYpQrUvIbPkLxPgRlHdJdUwTsBsMoUaMdOlQtNvKtWqRrFgIe" );
        search.setGroupBy( "ZfKiGiHjOuYdTbVnCyEwLdRoWoHeZnPhRrDdKwVoYyNlO" );
        search.setMyKey( "UyLmAiVwWeDqAfMnZbFzByAzOlDvSkImAuNyYxJxWqEeW" );
        search.setMysql( "MhFrIgQzPlKzUdNwLyGnSdLoUrSmIaVlUmAuSrPiQvBnOwZhWaArNvNrAtMpPmUjEgKbOoNaWvJeOnCmVjJsNdKpUoBbZrZlTaPjChIhXwPmPpCdVrXsDjAhTvIcKkDwBaDpOaFlJsKqCwGyNqLeMzTgRqLhRuBuKxNxLnRkYpDaLoYoTcLwKwDbJzUmIsJwMrGvDiAtZlWuHqRfWtMqNgUzPmObMeFlJwBeSzVfBgHuDoRbDvHvUkWyOtHtGhInOyKrZqCyDxCyGcOfSeTtGbKpLmYfEyLmPyDiUwElQcLmWlJoOpGhTsFuIeXcXpXnLnPmRtHoZrWqKrPnThLbKiUeFbOnKdHwQzXuOhCtJzHrIkVeZtVpTmTdJcYmZfTwIrEvYuRoPrHaSmJkNsFuShDxAjOrYvHmSvPhCwOoMrRuTlDzXoWvLaIaCrSsGaEaSjDkSjGeCkHgUpBtRtCxJuCaKlWeQvXbXaZoSwUdThCzDwHhLsGsXmSdWuFiUsKrOwYcIbQyUlIaYnEuBvUuXxRsIvUaUxAyHyEnJuXnVsIdIlZdLsPkThGkCkOmBgNpLcFsUyRmJvBsQnFgXwQlMbSbKuUxDgQbEiDwOvQfGyIoGcCnUhVpFpMgLyXwDfDpOaGzSuBmZjZeIdGaArDbZrRwCuDbCxLxCtNgPaWfQoOcZdOcLsXdMrXeWvZnVeVoEbViQpCoNgBrToNgWlOgIlFqVyKxWdQiEwCzWxLaVcXxAqSfShKfQfMvAsSzVfAdNoUzEbHdRgLtCaJeUaStLtVmClIjSwMpCyQdSwRhFmGmHbIqGwGdYxLzDaZwWeLuAbSuDvYoEhDhFdNzAjPjMeOyStHlXaZcKgMhUmDbPlBjLjZuHyHgBuVgIjEbXfApOuNaPyZpUsNdOlAvEaWyZuRzXjCgCbNjNkCmIwDjOvCjZsUgWjCtZoFcStYlIcGhNeFmFjLvRtEhPrUqYmUdCoFwLvPyIeMaAvWcXiQhOdCdHoCzWoLvKdJvPgRwWrCcXaMfIfFdJaLqVhHcRnQnPvTzZmIpGzApTdSvBnQwCvLgAkEbFcBjVwUhPgMcChPpPbKmAhQwDrAyZtEkGsMgDrAaWqFcIqPkXnTqCzZyWuRcYxSrCyHwQcPyVxMrJdIrOjZuKlHrWeVsBcYkKuWgKjOlTbBbUlUrKgWmKlCeWsJvMkRaBwSnIqXuZlRsZqJkUrYqSzKiLiMdTdQiSaIvHrJvGmMrCeSeSyNhQkIuQmQbNkVbDaVvRxTbQvQbLaSbRwJkLcWtWyYwHbJsZrGvYjNnNuIvVlXgIdHzRhBpJrTcSvKrAdSuUnNkFvZzMnBwAkFmQeMxIeJfVrFlEkHiBpYpDoMsZhWtSlNhDdWwAvAoSmVkHcPsKtTpGsDdKxIzGoFwUkYqKvLqLyPmSpFsWgZsMoKyBiSyOgBpCuPgPyElEoHjMfSaYuTmRmCoAwMjPbHiGxIpGxKcMhDvNwNmOkBfQxVjHiXgZxYyQkUsFvEyByLgHtSgTfFyQiUkYhDvGhIeGlVfRyLqMeGoJlBhEtItUsNaSoUkLfWwTtLgFpWmPpDdTeMaDkZvAcDaSyDsMwWfCoIlKmLiNeTlVmPoVaJlEjTzLhJfGyZlOvNdLaPkQzYkGuStQzMrIoHtZrHzSxQqJcEyEhPlGuQgMrTtZgUqKzDiQaYmKxQzNbUnHwXdToUrXxTtDbSuAyPxPtZaXmIyZsRrWaEyCoOfEjJaMjZvPsDbXzRxZvTcPuKmYjCwIcUtOkWcZxPePiCoUoWpNcEvIcYaRlZtMnRiAgFgUkQqIhTxFrIbDhUoHqNxLtBqTdTkZbIjFcUhQyHqKrLrTyRbPlJpJyImVtEaTpVvAeNqCkJjFzXdKmEdYaZkZjRdPhYmZzVvRbGbGsRpYmXoPmFlVxKfGuGjXrYdQtAsGkAhHbBkIwIcQlDqUmAeWjUoUvQnSbDbEoBsXoLoUoNnErStZzEtNuPeUiItVkNgOhZxKzNsXpZhJnFtCaUyWyOsRwUyQhWbQuXuWwFbIzNuUpFnGxEaGlKbFdRgMfQzIvDmCaReMeOqYsZxMrGyFuIsHzTvTeBcHeTyCxOaEvRoZpTcEaVtSlSqMxZdWzAwCiWnMqHeSwNhOdSfXtNqXmFaLnZtWgUoTnQxQcUpDpXvXwTiQfGmHeBrXnRxLgGtEtZfHeOfTkYuMmZkXiTuTzRgWxMwPjAyHlFlOpOeSlYhIxWtIkUlAyIdUfEaGoKsHaDbWmXaGpYtGiKsQsUgNxIfLhQrOeZsXwIbJrLgWvCfUsPiPvVkHnFcPbMsVyWoFpQyMaPgSpQvUmFsHrRcEzLqGsXyOlAfNkYeWzRgMtUmWjVhUmIsJnBnCfAcPkXcOwCxKnHpJkLtNpYkYbMdAoVgXrHrTqGnBtMuOcWnIgGgEiRfJnNdJtKhQnEtSbIvEjCfCgVpOdAaQkExWaTfUtDpMlDzUaCmSzSpRxOfHsGfSaZwAtPrLrPcEvWaNvDvCkRdInWoVpWaDeZnTmKwWgWxVmTaYmCnAeHsJyVhQkOcUyCaVxHrZvQhMkMoOsFvYkGyKzMjAoWtBeUnSqFjKgVdQzCsHmJoCpMsWwDeWtWxLkKmMdOoSdOuHhFlUfYzUeCtHeUpNgAwOaZfMfNnMhWtTzJbXwMzSjSmPwUxFwFrIqCxLkHtXmIcLvWcQsXtRnAjTsTgJsCmFzLqSyYrIjLrCcRnYyXjNnKcLySzPvSjClVtYvYsMwLbCwOzGyNyJmCjKyRsKfHdApKqWbWoImLeAsTkVfXrBtKtSoDdUeHuLlFhIgDrDcNvTlIvQiQnUyFwEfBrMrXtPaWsRmIeKaPvJmNmKkXxDpVsNuBmThIqGmQfEjKsRaYwQzSfUlHpEuWlDgXzNbXjKfOyAkMuYbKjQrXyDfLcWzQeTxCfMwCeTeYwGmTlYxQrQrOrXiIcZkQeVrVrTvTfVsGwGsKtCaPoEqIkEnPgVmIeKsDnLdTfVgYzBpJdDzOvNdMpMdBbKhBgIhJsWeWnJsTiJgPlVuXgGfJrQxSgCvMmOxVrPnUeWuEpOaHpXsOvEmVaLdVfEbAgPoSbDsLnUqFnAiTfGgOdJbNuQxErSjPoPoJuOvRvHwMaRrBwMiWbRaYpJnVyZgStHbNkOqYhGpDwVbKfOuFrGcVwOoXvPiQjLnEqUtOfIxTqGnIlWdToLlCqSpRkMmAjItKfDgYoNpTtKxOhJnHcByNxAtAoRiRlRuMwThCpOyPxFbOwAyIeMlDsSeZdNxKsSnOqWhPvCcWrPyGaSuXePjPxIsQdFrOpOyAoBcSjRgWiWnSwYnBeLwZvVcFeHrIbVlQhXnNdIlIzRhMnJpIhMoQpJgKiWyQzLmQiPzQxAmKcXnVeXwMkViKhTqDxMiCqQjEhYuDzRcXxIoNjWySdCqGmWhFbVgIxPkZhSeKpJiTiQiRlOwWaDoRjOrPxIdMdTfJbBwRgSgUgZfWmTgXwByYpOfTnBqWuTjGsJwMoMuRsEfNcCkVnYtEqXmFxZoGwPhSiDmPdMbGpXcRaSvSuDrSmIjJbIqVbYgPfInZtXqXoMbSjJzAoHbBlDvVsLtZqQxPyVyGyLgKzIqPmPfBiMaPeIiNyNlYaTqDkFkGrHsVpPoGaFaVwOxYcPkLcXrSmMqByUlTfUgTxSjGgWpDbUtKzFkNiPpOwOyGaUuQlQoYbUtQlIjWfUmPsNnQsUwKwZuLeXpHiLvXeSlCvLeMlNpMlFaDhYcOlQeMtBoBqFmWkTiJlTmWhUcMyKbJxYoViOtWmIqDtKkEwMtWpCtKuWpCfJdLoLcTeFaDzImRdZjRhRdQfSfFnIpLgDiNuThDmDmCmRzCqVoJfVqLsJwSfElKcRpEoPbSdUlWzQuPfNsQlMnCcZtMfFtOjAeTbLdKzNuFmFpDxCdGoGdGcBjHnVcWsBoPdUhJuEcJpNiLeHgZtWuGtZlIcOuKqCkWuAhOjKxZkAmGdAcJcIjEfAwWbPbCqXzDtGkPySoCiNdBnWjNvQoLuXyApWnDpElTpOgRxNtWqAvVkDoOaOaDdOzRrUtIeVxYaLoZpLxWzDvErXjChRdFwYtAmDcVyAkUiKmZqXkLbJsPgPkUfTsOhMvYqFyEhBaRaBaMaDcMwBaBzMzOkGcGmJnWzDcJrQsTaEqIeAlEwIjAnPnRiEmJnInTmPgImNvZuGlYmQiMoIqFpCsGbClTzRoOlOdVrFlFxCbTsUnBjSoFjJkCmNyHvLkOyNlDwBiKqUjYfWkQsLlXjYjPsMfGiXcKlZgLuKtLdYbLtYpEeUdJdImAmHcWkFnYwUvFuKeUrDqHiCgUfOjXtBgYhQtHdOxQqHgAsMgVxLnChAjMvQgNcIkAiWiToEcDeHtIzQgBzRrUtYhLuRyGtKuAbPfXoXnZqQbTxMlUeGeRjPsKwDvFrTpAnOcQlCwVrNySjAaXkWcTbStJgQqQtBuUoOxPcErMwShUmHcEqCyDiMeMqGcCbSrVqXfTdHmTzLzAyLzJnKwXlLfGbMlHoLyYmYsPqOvZrWoWcYsCqEnAlSfAhDuSgDsXmVgSnTyHmTpVtHxNmIyTqDxNlOcRdHcQqQoVfLkVzGvBwRuOdYsCaYsWqGuXxAuBuRaXdDbHfBdJtXlDxFnMrTgUgOnWpBxDuBtLbSjRjXzKhAkZlPsZgKiXbQgSkLhYiMwHcDsMsAaCsMlHxJmLzOaOxUjLdWwLpJhLlOkWwCaLeEpAkCtZyDySlXgAmWjWqBfDaMjIbLaKdJkDvVwXfFaAeGeDxTdZnPnKoBpJyHrDmZoFyFgNtEdJzNcZkGkHbFwOxDmQoOwJeEsLjUfQdYjMhYbIxWrIrUjEmZqFkMrFfCkNhXkXvQiZfYrXrEiQoVkEcPwEyBlCnYjHeMyRnNvIoVwWyOqQxFsJaDiIgDeAlFeBsImZuZoLyTyVbZyXrBrVdTcJaHhVhHzTbMwVbUjHoBvSlLzPkOxDp" );
        search.setOrderBy( "LwBqOgEeEpTmZkUsZoXyUkFkApHkOdFqJzXsNzFaPqMvV" );
        search.setPageSize( 20l );
        search.setSearchName( "OkLmMxGrKmVrWyXeWjEmWsXcCcEgYtBwFwCkSbIiNrShVcLiLh" );
        search.setTitle( "YwLyXpFlCvMzFhIvJpTeMhAnPeUnAxTuZoZpKtWtVcDoGaPxNtVdIfYsTiDmTeZtDcGlUdNmDhSwDqNh" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( search ) ) );
            }
        } );

        manager.save( search );
    }

    @Test
    public void testRemoveSearch() {
        log.debug( "testing remove..." );

        final String searchId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( searchId ) ) );
            }
        } );

        manager.remove( searchId );
    }
}