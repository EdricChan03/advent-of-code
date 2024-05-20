package com.edricchan.aoc.puzzle.input

import com.edricchan.aoc.puzzle.Puzzle
import com.edricchan.aoc.puzzle.PuzzleMeta
import com.edricchan.aoc.puzzle.ResourceLoader
import com.edricchan.aoc.puzzle.getInputPath
import com.edricchan.aoc.puzzle.input.PuzzleInput.RawList.Companion.lineSeparator
import java.nio.file.Path
import java.time.Year
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.io.path.useLines

/**
 * Represents input data for a [Puzzle].
 *
 * Its implementations are:
 * * [PuzzleInput.Raw] - puzzle input which holds raw text
 * * [PuzzleInput.RawList] - puzzle input which holds raw text as a list of strings
 * * [PuzzleInput.File] - puzzle input from the specified [Path]
 * * [PuzzleInput.ResourceFile] - puzzle input from Java resources, see [ResourceLoader]
 * * [PuzzleInput.Default] - the default implementation if no explicit puzzle input is
 * specified (and for backwards compatibility)
 */
sealed interface PuzzleInput {
    /**
     * Retrieves the list of lines from the given input.
     * @see CharSequence.lines
     * @see Path.readLines
     */
    fun lines(): List<String>

    /**
     * Retrieves the raw text from the given input.
     * @see Path.readText
     */
    fun raw(): String

    /**
     * Calls the block callback giving it a sequence of all the lines in the input
     * and optionally closes the reader once the processing is complete.
     * @see CharSequence.lineSequence
     * @see Path.useLines
     */
    fun <T> useLines(block: (Sequence<String>) -> T): T

    /** The display name to use when printing this class. */
    val displayName get() = toString()

    /**
     * An implementation of [PuzzleInput] that uses raw text.
     * @property text The raw text.
     */
    @JvmInline
    value class Raw(val text: String) : PuzzleInput {
        override fun lines() = text.lines()

        override fun raw() = text

        override fun <T> useLines(block: (Sequence<String>) -> T) = block(text.lineSequence())
    }

    /**
     * An implementation of [PuzzleInput] that uses raw text as a list of strings.
     *
     * ## Implementation notes
     * * The [raw] method will [join][Iterable.joinToString] the [texts] items with the
     * [`\n` line separator][lineSeparator].
     * * The [lines] method will return the [texts] value _as-is_, without any
     * additional processing (for example, removing line-separators).
     * @property texts The raw text as a list of strings.
     */
    @JvmInline
    value class RawList(val texts: List<String>) : PuzzleInput {
        override fun lines() = texts

        /** Retrieves the raw text from its input, joined by `\n`. */
        override fun raw() = texts.joinToString(lineSeparator)

        override fun <T> useLines(block: (Sequence<String>) -> T) = block(texts.asSequence())

        companion object {
            /** The line-separator character to use for [raw]. */
            const val lineSeparator: String = "\n"
        }
    }

    /**
     * An implementation of [PuzzleInput] that uses a file [Path].
     * @property path [Path] to the specified input file.
     */
    data class File(val path: Path) : PuzzleInput {
        override fun lines() = path.readLines()

        override fun raw() = path.readText()

        override fun <T> useLines(block: (Sequence<String>) -> T) = path.useLines(block = block)

        override val displayName: String
            get() = "File - $path"
    }

    /**
     * An implementation of [PuzzleInput] that uses a [ResourceLoader] with the given
     * [resourceName].
     * @property resourceName The resource name to retrieve.
     * @property resourceLoader [ResourceLoader] used to retrieve the [resourceName].
     * @see ResourceLoader.requireResourceAsPath
     */
    data class ResourceFile(
        val resourceName: String,
        val resourceLoader: ResourceLoader = ResourceLoader.Default
    ) : PuzzleInput {
        val path by lazy { resourceLoader.requireResourceAsPath(resourceName) }
        override fun lines() = path.readLines()

        override fun raw() = path.readText()

        override fun <T> useLines(block: (Sequence<String>) -> T) = path.useLines(block = block)

        override val displayName: String
            get() = "ResourceFile (resource name: $resourceName) - $path"
    }

    /**
     * The default implementation of [PuzzleInput] if not specified.
     *
     * The input data is read using the [resourceLoader] provided.
     * @see getInputPath
     */
    data class Default(
        val meta: PuzzleMeta,
        val inputFileName: String = Puzzle.defaultInputFileName,
        val resourceLoader: ResourceLoader = ResourceLoader.Default
    ) : PuzzleInput {
        constructor(
            year: Year,
            day: Int,
            inputFileName: String = Puzzle.defaultInputFileName,
            resourceLoader: ResourceLoader = ResourceLoader.Default
        ) : this(
            meta = PuzzleMeta(year, day),
            inputFileName = inputFileName,
            resourceLoader = resourceLoader
        )

        constructor(
            year: Int,
            day: Int,
            inputFileName: String = Puzzle.defaultInputFileName,
            resourceLoader: ResourceLoader = ResourceLoader.Default
        ) : this(
            year = Year.of(year),
            day = day,
            inputFileName = inputFileName,
            resourceLoader = resourceLoader
        )

        /** Path to the input file. */
        val path by lazy { meta.getInputPath(inputFileName) }

        override fun lines() = path.readLines()

        override fun raw() = path.readText()

        override fun <T> useLines(block: (Sequence<String>) -> T) = path.useLines(block = block)

        override val displayName: String
            get() = "Default (metadata: $meta) - $path"
    }
}

//#region Extension functions/properties
/** Converts the receiver [String] to its [PuzzleInput.Raw] form. */
fun String.toPuzzleInput(): PuzzleInput.Raw = PuzzleInput.Raw(this)

/** Converts the receiver [String] to its [PuzzleInput.Raw] form. */
val String.input: PuzzleInput.Raw get() = PuzzleInput.Raw(this)

/** Converts the receiver [List] to its [PuzzleInput.RaRawListw] form. */
fun List<String>.toPuzzleInput(): PuzzleInput.RawList = PuzzleInput.RawList(this)

/** Converts the receiver [List] to its [PuzzleInput.RawList] form. */
val List<String>.input: PuzzleInput.RawList get() = PuzzleInput.RawList(this)

/** Converts the receiver [Path] to its [PuzzleInput.File] form. */
fun Path.toPuzzleInput(): PuzzleInput.File = PuzzleInput.File(this)

/** Converts the receiver [Path] to its [PuzzleInput.File] form. */
val Path.input: PuzzleInput.File get() = PuzzleInput.File(this)

/**
 * Retrieves the [puzzle input][PuzzleInput.ResourceFile] given the specified arguments.
 * @receiver The resource loader to resolve the [resourceName].
 * @param resourceName The resource's name to resolve.
 */
fun ResourceLoader.getPuzzleInput(
    resourceName: String
): PuzzleInput.ResourceFile = PuzzleInput.ResourceFile(resourceName, this)

/** Converts the receiver [PuzzleMeta] to its [PuzzleInput.Default] form. */
val PuzzleMeta.input: PuzzleInput.Default get() = PuzzleInput.Default(this)

/**
 * Retrieves the [puzzle input][PuzzleInput.Default] given the specified arguments.
 * @receiver The puzzle metadata.
 * @param inputFileName The input's file name.
 * @param resourceLoader Resource loader to resolve the [inputFileName].
 */
fun PuzzleMeta.getPuzzleInput(
    inputFileName: String,
    resourceLoader: ResourceLoader = ResourceLoader.Default
): PuzzleInput.Default = PuzzleInput.Default(this, inputFileName, resourceLoader)
//#endregion
