import VerticalSizedFIFODeque from "./VerticalSizedFIFODeque";

test('Appending an element increases the size by 1', () => {
    let q = new VerticalSizedFIFODeque(10);
    q.append("TEST");
    let result = q.return();

    expect(q.size).toBe(1)
    expect(q.verifySize()).toBe(1)
    expect(result).toHaveLength(1);
});

test('Appending n elements increase the size by n', () => {
    let q = new VerticalSizedFIFODeque(10);
    q.append("TEST 1");
    q.append("TEST 2");

    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
});

test('Prepending an element increases the size by 1', () => {
    let q = new VerticalSizedFIFODeque(10);
    q.prepend("TEST");
    let result = q.return();

    expect(q.size).toBe(1)
    expect(q.verifySize()).toBe(1)
    expect(result).toHaveLength(1);
});

test('Prepending n elements increase the size n', () => {
    let q = new VerticalSizedFIFODeque(10);
    q.prepend("TEST 1");
    q.prepend("TEST 2");
    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
});

test('Appending and prepending work well together to increase size', () => {
    let q = new VerticalSizedFIFODeque(10);
    q.append("TEST 1");
    q.prepend("TEST 2");
    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
});

test('Appending to a full list pops first item off', () => {
    let q = new VerticalSizedFIFODeque(2);
    q.append("TEST 1");
    q.append("TEST 2");
    q.append("TEST 3");
    let result = q.return();

    expect(q.size).toBe(2)
    expect(q.verifySize()).toBe(2)
    expect(result).toHaveLength(2);
    expect(result).toEqual(["TEST 2", "TEST 3"])
});

