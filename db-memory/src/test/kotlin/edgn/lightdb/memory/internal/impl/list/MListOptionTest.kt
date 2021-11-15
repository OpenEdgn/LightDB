package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.memory.internal.universal.MemoryDataConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Optional

internal class MListOptionTest {

    data class TestDataA(
        val name: String
    )

    data class TestDataB(
        val name: String
    )

    @Test
    fun get() {
        val mListOption = MListOption(MemoryDataConfig())
        assertEquals(mListOption.get("test.a", TestDataA::class), Optional.empty<TestDataA>())
        val createDataA = mListOption.getOrCreate("test.a", TestDataA::class)
        assertEquals(mListOption.get("test.a", TestDataB::class), Optional.empty<TestDataB>())
        assertTrue(createDataA.available)
    }

    @Test
    fun drop() {
        val mListOption = MListOption(MemoryDataConfig())
        assertFalse(mListOption.drop("test.a", TestDataA::class))
        val createDataA = mListOption.getOrCreate("test.a", TestDataA::class)
        createDataA.get().get().add(TestDataA("TEST"))
        assertTrue(mListOption.drop("test.a", TestDataA::class))
        assertFalse(mListOption.drop("test.a", TestDataA::class))
    }

    @Test
    fun exists() {
        val mListOption = MListOption(MemoryDataConfig())
        assertFalse(mListOption.exists("test.a", TestDataA::class))
        mListOption.getOrCreate("test.a", TestDataA::class)
        assertTrue(mListOption.exists("test.a", TestDataA::class))
    }
}
