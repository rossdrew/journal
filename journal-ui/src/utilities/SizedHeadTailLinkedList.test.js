import SizedHeadTailLinkedList from "./SizedHeadTailLinkedList";

test('Appending an element increases the size by 1', () => {
    let q = new SizedHeadTailLinkedList(10);
    q.append("TEST");
    let result = q.return();

    expect(q.size).toBe(1)
    expect(q.verifySize()).toBe(1)
    expect(result).toHaveLength(1);
});

test('Appending n elements increase the size by n', () => {
    let q = new SizedHeadTailLinkedList(10);
    q.append("TEST 1");
    q.append("TEST 2");

    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
});

test('Prepending an element increases the size by 1', () => {
    let q = new SizedHeadTailLinkedList(10);
    q.prepend("TEST");
    let result = q.return();

    expect(q.size).toBe(1)
    expect(q.verifySize()).toBe(1)
    expect(result).toHaveLength(1);
});

test('Prepending n elements increase the size n', () => {
    let q = new SizedHeadTailLinkedList(10);
    q.prepend("TEST 1");
    q.prepend("TEST 2");
    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
});

test('Appending and prepending work well together to increase size', () => {
    let q = new SizedHeadTailLinkedList(10);
    q.append("TEST 1");
    q.prepend("TEST 2");
    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
});

test('Appending to a full list pops first item off', () => {
    let q = new SizedHeadTailLinkedList(2);
    q.append("TEST 1");
    q.append("TEST 2");
    q.append("TEST 3");
    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
    expect(result).toEqual(["TEST 2", "TEST 3"])
});

test('Prepending to a full list pops last item off', () => {
    let q = new SizedHeadTailLinkedList(2);
    q.prepend("TEST 1");
    q.prepend("TEST 2");
    q.prepend("TEST 3");
    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
    expect(result).toEqual(["TEST 3", "TEST 2"])
});

test('Complex append/prepend drop the correct items', () => {
    let q = new SizedHeadTailLinkedList(3);
    q.prepend("TEST 1"); // >[1|.|.]
    q.append("TEST 2"); // [1|.|2]<
    q.prepend("TEST 3"); // >[3|1|2]
    q.prepend("TEST 4"); // >[4|3|1] (2)
    q.append("TEST 5"); // (4) [3|1|5]<
    let result = q.return();

    expect(q.size).toBe(3)
    expect(q.verifySize()).toBe(3)
    expect(result).toHaveLength(3);
    expect(result).toEqual(["TEST 3", "TEST 1", "TEST 5"])
});

test('Deep cloning works', () => {
    let q = new SizedHeadTailLinkedList(10);
    q.append("TEST");
    let result = q.return();
    let clone = q.deepClone()

    expect(q.size).toBe(1)
    expect(result).toHaveLength(1);
    expect(clone.size).toBe(1);
    expect(clone.return()).toEqual(["TEST"])
});

