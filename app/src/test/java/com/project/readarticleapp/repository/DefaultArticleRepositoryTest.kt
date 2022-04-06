package com.project.readarticleapp.repository

import junit.framework.TestCase
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DefaultArticleRepositoryTest : TestCase() {

    private lateinit var mBaseRepository: BaseRepository

    @BeforeClass
    override fun setUp() {
        super.setUp()
        mBaseRepository = BaseRepository()
        mBaseRepository.setUp()

    }

    @AfterClass
    override fun tearDown() {
        mBaseRepository.tearDown()
        super.tearDown()
    }
}