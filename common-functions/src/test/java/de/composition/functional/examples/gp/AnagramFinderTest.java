package de.composition.functional.examples.gp;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Tutorial code to introduce some guava library features emphasizing functional
 * aspects of the library.
 * 
 * Anagram Finder - Problem 77 at http://www.4clojure.com
 * <p/>
 * Here we try to implement a similar (functional!) solution in java using
 * guava.
 * <p/>
 * Write a function which finds all the anagrams in a vector of words. A word x
 * is an anagram of word y if all the letters in x can be rearranged in a
 * different order to form y. Your function should return a set of sets, where
 * each sub-set is a group of words which are anagrams of each other. Each
 * sub-set should have at least two words. Words without any anagrams should not
 * be included in the result.
 * 
 * <p/>
 * clojure solution:
 * 
 * <pre>
 * <code>
 * (fn [words] (->> words (group-by sort) vals (map set) (filter #(> (count %) 1)) set))
 * </code>
 * </pre>
 */
@SuppressWarnings("unchecked")
public class AnagramFinderTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void findAnagrams_passNull_returnsEmptyList() {
		List<String> words = null;

		AnagramFinder.findAnagrams(words);
	}

	@Test
	public void findAnagrams_emptyList_returnsEmptyList() {
		List<String> words = newArrayList();

		Set<Set<String>> anagramsSet = AnagramFinder.findAnagrams(words);

		assertEquals(newHashSet(), anagramsSet);
	}

	@Test
	public void findAnagrams_singleWord_returnsEmptyList() {
		List<String> words = newArrayList("meat");

		Set<Set<String>> anagramsSet = AnagramFinder.findAnagrams(words);

		assertEquals(newHashSet(), anagramsSet);
	}

	@Test
	public void findAnagrams_multipleWordsButNotAnagrams_returnsEmptyList() {
		List<String> words = newArrayList("meat", "meet");

		Set<Set<String>> anagramsSet = AnagramFinder.findAnagrams(words);

		assertEquals(newHashSet(), anagramsSet);
	}

	@Test
	public void findAnagrams_listWithAnagramPair_returnsSetWithPair() {
		List<String> words = newArrayList("meat", "team");

		Set<Set<String>> anagramsSet = AnagramFinder.findAnagrams(words);

		assertEquals(Sets.<Set<String>> newHashSet(newHashSet("meat", "team")), anagramsSet);
	}

	@Test
	public void findAnagrams_listWithMultipleAnagramPairs_returnsSetWithPairs() {
		List<String> words = newArrayList("veer", "lake", "item", "kale", "mite", "ever");

		Set<Set<String>> anagramsSet = AnagramFinder.findAnagrams(words);
		// Set<Set<String>> anagramsSet =
		// AnagramFinder.findAnagrams_allInlined(words);

		assertEquals(newHashSet(newHashSet("veer", "ever"), newHashSet("lake", "kale"), newHashSet("mite", "item")),
				anagramsSet);
	}

	@Test
	public void findAnagrams_triplesAndPairsAndNonAnagrams_groupsAnagramsAndDropsNonAnagrams() {
		List<String> words = newArrayList("meat", "mat", "team", "mate", "eat", "tea");

		Set<Set<String>> anagramsSet = AnagramFinder.findAnagrams(words);

		assertEquals(newHashSet(newHashSet("meat", "team", "mate"), newHashSet("eat", "tea")), anagramsSet);
	}

}
