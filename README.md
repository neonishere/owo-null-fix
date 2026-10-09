# Owo Null Fix

NeoForge 1.21.1. Requires owo-lib. Install on client and server.

Fixes a disconnect when armor insulated with Cold Sweat is put in an Accessories cosmetic
slot. The player gets kicked with

    Failed decoding custom payload accessories:main
    Cannot invoke "io.wispforest.endec.format.edm.EdmElement.value()" because "input" is null

and can't rejoin, because the item is still in the slot.

The cause is in owo-lib. Its EdmOps throws when a codec hands it null, where NBT and JSON
ops return an error result. Cold Sweat reads optional fields in a way that depends on the
error result. This mod adds the null check to the read methods of EdmOps and changes
nothing else.

Not affiliated with owo-lib. If it gets fixed there this mod is no longer needed.
