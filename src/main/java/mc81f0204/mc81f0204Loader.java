/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mc81f0204;

import java.io.IOException;
import java.util.*;

import ghidra.app.util.Option;
import ghidra.app.util.bin.ByteProvider;
import ghidra.app.util.importer.MemoryConflictHandler;
import ghidra.app.util.importer.MessageLog;
import ghidra.app.util.opinion.AbstractLibrarySupportLoader;
import ghidra.app.util.opinion.LoadSpec;
import ghidra.framework.model.DomainObject;
import ghidra.program.model.listing.Program;
import ghidra.util.exception.CancelledException;
import ghidra.util.task.TaskMonitor;

/**
 * TODO: Provide class-level documentation that describes what this loader does.
 */
public class mc81f4204Loader extends AbstractLibrarySupportLoader {
	public final static String MC81F4204_MAGIC = new String(new byte[] {0x60, 0x1e, 0x00});
	
	@Override
	public String getName() {

		// TODO: Name the loader.  This name must match the name of the loader in the .opinion 
		// files.

		return "MC81F4204";
	}

	@Override
	public Collection<LoadSpec> findSupportedLoadSpecs(ByteProvider provider) throws IOException {
		List<LoadSpec> loadSpecs = new ArrayList<>();
		// Can look at https://github.com/andr3colonel/ghidra_wasm/blob/master/src/main/java/wasm/WasmLoader.java 
		// for more complex loading and reading of different sections. 
		BinaryReader reader = new BinaryReader(provider, true);
		
		magic = reader.readNextByteArray( 3 );

		if (MC81F4204_MAGIC.equals(new String(magic))) {
			List<QueryResult> queries = QueryOpinionService.query(getName(), WasmConstants.MACHINE, null);
			for (QueryResult result : queries) {
				loadSpecs.add(new LoadSpec(this, 0, result));
				loadSpecs.add(new LoadSpec(this, 0, result)); // why is this repeated? 
				loadSpecs.add(new LoadSpec(this, 0, result));
				loadSpecs.add(new LoadSpec(this, 0, result));
				loadSpecs.add(new LoadSpec(this, 0, result));
			}
		} else {
			throw new IOException("not a MC81F4204 file.");
		}
		

		return loadSpecs;
	}

	@Override
	protected void load(ByteProvider provider, LoadSpec loadSpec, List<Option> options,
			Program program, MemoryConflictHandler handler, TaskMonitor monitor, MessageLog log)
			throws CancelledException, IOException {

		// TODO: Load the bytes from 'provider' into the 'program'.
	}

	@Override
	public List<Option> getDefaultOptions(ByteProvider provider, LoadSpec loadSpec,
			DomainObject domainObject, boolean isLoadIntoProgram) {
		List<Option> list =
			super.getDefaultOptions(provider, loadSpec, domainObject, isLoadIntoProgram);

		// TODO: If this loader has custom options, add them to 'list'
		// list.add(new Option("Option name goes here", "Default option value goes here"));

		return list;
	}

	@Override
	public String validateOptions(ByteProvider provider, LoadSpec loadSpec, List<Option> options) {

		// TODO: If this loader has custom options, validate them here.  Not all options require
		// validation.

		return super.validateOptions(provider, loadSpec, options);
	}
}
